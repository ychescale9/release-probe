package reactivecircus.releaseprobe.browsecollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_browse_collections.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.releaseprobe.core.base.BaseFragment
import reactivecircus.releaseprobe.domain.artifactcollection.model.ArtifactCollection
import timber.log.Timber

@ExperimentalCoroutinesApi
class BrowseCollectionsFragment : BaseFragment() {

    private lateinit var artifactCollectionsAdapter: ArtifactCollectionsAdapter

    private val viewModel by viewModel<ArtifactCollectionsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_browse_collections, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artifactCollectionsAdapter = ArtifactCollectionsAdapter(actionListener)
        artifactCollectionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = artifactCollectionsAdapter
        }

        viewModel.artifactCollectionsLiveData.observe(this) {
            artifactCollectionsAdapter.submitList(it)
        }
    }

    private val actionListener: ArtifactCollectionsAdapter.ActionListener =
        object : ArtifactCollectionsAdapter.ActionListener {
            override fun onItemClick(artifactCollection: ArtifactCollection) {
                // TODO
                Timber.d("Clicked artifact collection.")
            }
        }
}
