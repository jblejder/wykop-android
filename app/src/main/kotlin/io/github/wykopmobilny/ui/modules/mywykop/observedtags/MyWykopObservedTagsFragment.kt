package io.github.wykopmobilny.ui.modules.mywykop.observedtags

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.wykopmobilny.R
import io.github.wykopmobilny.api.responses.ObservedTagResponse
import io.github.wykopmobilny.base.BaseFragment
import io.github.wykopmobilny.databinding.ActivityConversationsListBinding
import io.github.wykopmobilny.models.fragments.DataFragment
import io.github.wykopmobilny.models.fragments.getDataFragmentInstance
import io.github.wykopmobilny.models.fragments.removeDataFragment
import io.github.wykopmobilny.ui.adapters.ObservedTagsAdapter
import io.github.wykopmobilny.ui.modules.mywykop.MyWykopNotifier
import io.github.wykopmobilny.utils.prepare
import io.github.wykopmobilny.utils.viewBinding
import javax.inject.Inject

class MyWykopObservedTagsFragment :
    BaseFragment(R.layout.activity_conversations_list),
    MyWykopObservedTagsView,
    SwipeRefreshLayout.OnRefreshListener,
    MyWykopNotifier {

    companion object {
        const val DATA_FRAGMENT_TAG = "MYWYKOP_OBSERVED_TAGS_FRAGMENT_TAG"

        fun newInstance() = MyWykopObservedTagsFragment()
    }

    @Inject
    lateinit var adapter: ObservedTagsAdapter

    @Inject
    lateinit var presenter: MyWykopObservedTagsPresenter

    private val binding by viewBinding(ActivityConversationsListBinding::bind)

    lateinit var dataFragment: DataFragment<List<ObservedTagResponse>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.subscribe(this)
        dataFragment = childFragmentManager.getDataFragmentInstance(DATA_FRAGMENT_TAG)
        presenter.subscribe(this)
        binding.swiperefresh.setOnRefreshListener(this)
        binding.recyclerView.prepare()
        binding.recyclerView.adapter = adapter

        if (dataFragment.data != null) {
            adapter.items.clear()
            adapter.items.addAll(dataFragment.data!!)
            adapter.notifyDataSetChanged()
        } else {
            binding.loadingView.isVisible = true
            presenter.loadTags()
        }
    }

    override fun onDestroyView() {
        presenter.unsubscribe()
        super.onDestroyView()
    }

    override fun onRefresh() {
        if (!binding.swiperefresh.isRefreshing) {
            binding.swiperefresh.isRefreshing = true
        }
        presenter.loadTags()
    }

    override fun showTags(tags: List<ObservedTagResponse>) {
        binding.loadingView.isVisible = false
        binding.swiperefresh.isRefreshing = false
        adapter.items.clear()
        adapter.items.addAll(tags)
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dataFragment.data = adapter.items
    }

    override fun onPause() {
        if (isRemoving) {
            childFragmentManager.removeDataFragment(dataFragment)
        }
        super.onPause()
    }

    override fun removeDataFragment() = childFragmentManager.removeDataFragment(dataFragment)
}
