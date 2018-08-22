package ychescale9.releaseprobe.data.artifactcollection.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Flowable
import org.junit.Test
import ychescale9.releaseprobe.data.artifactcollection.DefaultArtifactCollections
import ychescale9.releaseprobe.data.artifactcollection.mapper.ArtifactCollectionEntityToModel
import ychescale9.releaseprobe.domain.artifactcollection.model.ArtifactCollection
import ychescale9.releaseprobe.persistence.artifactcollection.dao.ArtifactCollectionDao
import ychescale9.releaseprobe.persistence.artifactcollection.entity.ArtifactCollectionEntity

class ArtifactCollectionRepositoryImplTest {

    private val dummyArtifactCollections = listOf(
            ArtifactCollection(
                    "AndroidX",
                    "Android extension libraries - a repackage of the Android Support Library, following semantic versioning",
                    "#60AF46",
                    listOf("androidx", "com.google.android.material")
            ),
            ArtifactCollection(
                    "Firebase",
                    "Google's mobile platform for developing apps, improving app quality and growing business.",
                    "#D55D09",
                    listOf("com.google.firebase", "com.crashlytics.sdk.android")
            )
    )

    private val artifactCollectionDao = mockk<ArtifactCollectionDao>(relaxUnitFun = true) {
        every { allArtifactCollections() } returns Flowable.just(listOf())
    }

    private val defaultArtifactCollections = DefaultArtifactCollections()

    private val mapper = mockk<ArtifactCollectionEntityToModel> {
        every { transform(any<List<ArtifactCollectionEntity>>()) } returns dummyArtifactCollections
    }

    private val artifactCollectionRepository = ArtifactCollectionRepositoryImpl(
            artifactCollectionDao,
            mapper,
            defaultArtifactCollections
    )

    @Test
    fun `should get artifact collections from dao when subscribed`() {
        val testObserver = artifactCollectionRepository.getArtifactCollections().test()
        verify(exactly = 1) {
            artifactCollectionDao.allArtifactCollections()
            mapper.transform(any<List<ArtifactCollectionEntity>>())
        }
        testObserver.assertValue(dummyArtifactCollections)
    }

    @Test
    fun `should insert default artifact collections to dao when subscribed`() {
        val testObserver = artifactCollectionRepository.insertDefaultArtifactCollections().test()
        verify(exactly = 1) {
            artifactCollectionDao.insertArtifactCollections(defaultArtifactCollections.get())
        }
        testObserver.assertValue(true)
    }
}