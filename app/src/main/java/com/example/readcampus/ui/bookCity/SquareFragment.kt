package com.example.readcampus.ui.bookCity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.example.readcampus.R
import com.example.readcampus.base.BaseFragment
import com.example.readcampus.bean.BookBean
import com.example.readcampus.databinding.FragmentSquareBinding
import com.example.readcampus.ui.bookShelf.RVOScrollListener
import com.example.readcampus.ui.bookShelf.SquareAdapter


class SquareFragment: BaseFragment() {

    private var _binding: FragmentSquareBinding? = null
    private val binding get() = _binding!!


    private lateinit var squareAdapter: SquareAdapter
    private lateinit var viewModel: SquareViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SquareViewModel::class.java]
        _binding = FragmentSquareBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * 初始化界面
     */
    override fun initView() {
        // 刷新框架主题色
        binding.refreshLayout.setColorSchemeResources(R.color.colorAccent)

        //初始化列表适配器
        squareAdapter = SquareAdapter()

        // 初始化主显示界面
        binding.rvTypes.apply {
            adapter = squareAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /**
     * 初始化监听
     */
    @SuppressLint("CheckResult")
    override fun initListener() {
        // 小说书城弹出框事件

        binding.includeSearchTitle.tvSearchTitle.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(text = "书城分类")
                listItems(items = viewModel.getParses()) { _, _, text ->
                    viewModel.fetchShopName(text.toString())
                    val typeName = viewModel.getTypes()[0]
                    searchData(typeName = typeName)
                }
                lifecycleOwner(requireActivity())
            }
        }

        // 小说分类弹出框事件
        binding.includeSearchTitle.tvSearchFilter.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(text = "小说分类")
                listItems(items = viewModel.getTypes()) { _, _, text ->
                    searchData(typeName = text.toString())
                }
                lifecycleOwner(requireActivity())
            }
        }

        // 设置搜索事件
        binding.includeSearchTitle.tvSearchSearch.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(text = "搜索小说")
                input(
                    hint = "输入 书名、作者",
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { _, text ->
                    val keyword = text.toString()
                    if (keyword.isNotBlank()) {
                        searchData(keyWord = keyword)
                    } else {
                        viewModel.toastMsg("关键字不能为空")
                    }
                }
                positiveButton(text = "搜索")
            }
        }

        // 刷新事件
        binding.refreshLayout.setOnRefreshListener {
            viewModel.fetchSearch(1)
        }

        // 点击项目事件
        squareAdapter.itemClickListener = object : SquareAdapter.OnBookItemClickListener {
            override fun openItem(bookBean: BookBean) {
                viewModel.fetchBookInfo(bookBean.url)
            }
        }

        //添加列表滚动事件
        binding.rvTypes.addOnScrollListener(object :
            RVOScrollListener(binding.rvTypes.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.fetchSearch()
            }

            override fun totalPageCount(): Int {
                return viewModel.getTotalPage()
            }

            override fun isLastPage(): Boolean {
                return viewModel.isLastPage()
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading()
            }
        })

        //错误通知事件
        viewModel.toast.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        }

        //是否显示加载框
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = it
        }

        //根据类型搜索
        viewModel.searchType.observe(viewLifecycleOwner) {
            fetchPage(it.currentPage, it.totalPage, it.bookBeans)
        }

        //根据关键字搜索
        viewModel.searchKeyWord.observe(viewLifecycleOwner) {
            fetchPage(it.currentPage, it.totalPage, it.bookBeans)
        }

        //新增书籍
        viewModel.bookInserted.observe(viewLifecycleOwner) {
            viewModel.toastMsg("加入书架成功")
        }

        //更新书籍信息
        viewModel.bookInfo.observe(viewLifecycleOwner) {
            if (it?.bookBean != null) {
                val bookBean = it.bookBean
                viewModel.fetchBook(bookBean)
                //小说详情
                MaterialDialog(requireContext()).show {
                    title(text = bookBean.name)
                    //icon(R.mipmap.ic_launcher)
                    //icon(drawable = image)
                    message(text = "作者：${bookBean.author}\n类别：${bookBean.category}\n状态：${bookBean.status}\n简介：${bookBean.desc}")
                    positiveButton(text = "开始阅读") {
                        val bookBean = viewModel.getBookBean()
                        if (bookBean != null) {
                            // 打开 书籍
                            //NovelReadActivity.startFromActivity(requireActivity(), bookBean)
                        } else {
                            //viewModel.toastMsg("打开书籍异常，请重新获取书籍")
                        }
                    }
                    negativeButton(text = "加入书架") {
                        //加入书架
                        viewModel.insertBook()
                    }
                    lifecycleOwner(requireActivity())
                }
            }
        }
    }

    /**
     * 初始化数据
     */
    override fun initData() {
        val parseName = viewModel.getParses()[0]
        viewModel.fetchShopName(parseName)

        val typeName = viewModel.getTypes()[0]
        searchData(typeName = typeName)
    }

    /**
     * 填充页面
     */
    private fun fetchPage(currentPage:Int,totalPage:Int,bookList:List<BookBean>) {
        if (bookList.isNotEmpty()) {
            if (currentPage == 1) {
                squareAdapter.refreshItems(bookList)
            } else {
                squareAdapter.addItems(bookList)
            }
        }
        viewModel.fetchPage(currentPage + 1, totalPage)
        binding.refreshLayout.isRefreshing = false
    }

    /**
     * 加载数据
     */
    private fun searchData(
        keyWord: String = "",
        typeName: String = ""
    ) {
        if (keyWord.isBlank()) {
            // 根据类型搜索
            viewModel.fetchSearchType(typeName, 1)
            binding.includeSearchTitle.tvSearchTitle.text = viewModel.getShopName()
            binding.includeSearchTitle.tvSearchFilter.text = typeName
        } else {
            //根据关键字搜索
            viewModel.fetchSearchKeyWord(keyWord, 1)
            binding.includeSearchTitle.tvSearchTitle.text = viewModel.getShopName()
            binding.includeSearchTitle.tvSearchFilter.text = keyWord
        }
    }
}