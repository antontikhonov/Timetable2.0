package ru.antontikhonov.android.timetableistu.ui.screen.news

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.architecture.State
import ru.antontikhonov.android.timetableistu.databinding.FragmentNewsBinding
import ru.antontikhonov.android.timetableistu.pojo.NewsEntity
import ru.antontikhonov.android.timetableistu.ui.screen.showToast

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val viewBinding: FragmentNewsBinding by viewBinding(FragmentNewsBinding::bind)
    private val viewModel: NewsViewModel by viewModel()
    private val adapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            state.error?.let {
                requireContext().showToast(getString(R.string.error_message))
            }
        }
    }
}
