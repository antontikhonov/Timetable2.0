package site.antontikhonov.android.timetableistu.ui.screen.news

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import site.antontikhonov.android.timetableistu.R
import site.antontikhonov.android.timetableistu.architecture.State
import site.antontikhonov.android.timetableistu.databinding.FragmentNewsBinding
import site.antontikhonov.android.timetableistu.pojo.NewsEntity

class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var viewBinding: FragmentNewsBinding
    private val viewModel by viewModel<NewsViewModel>()
    private val adapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentNewsBinding.bind(view)
        initView()
        viewModel.data.observe(viewLifecycleOwner, ::renderUi)
        viewModel.loadNews()
    }

    private fun initView() {
        with(viewBinding) {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(NewsLinearDecoration(R.dimen.theme_grid_spacing))
            (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                it.reverseLayout = true
            }
        }
    }

    private fun renderUi(state: State<List<NewsEntity>>) {
        with(viewBinding) {
            progressBar.isVisible = state.loading
            recyclerView.isVisible = !state.loading
            state.content?.let {
                adapter.submitList(it.reversed())
            }
        }
    }
}