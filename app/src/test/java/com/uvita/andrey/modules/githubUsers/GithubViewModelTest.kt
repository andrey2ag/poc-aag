package com.uvita.andrey.modules.githubUsers

import com.uvita.andrey.general.LogUtil
import com.uvita.andrey.modules.repository.local.AppDB
import com.uvita.andrey.modules.repository.remote.github.GithubRepository
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox

@PowerMockIgnore("javax.net.ssl.*")
@RunWith(PowerMockRunner::class)
@PrepareForTest(LogUtil::class)
class GithubViewModelTest {

    private lateinit var viewModel: GithubViewModel
    private lateinit var view: GithubView
    private lateinit var repo: GithubRepository

    @Before
    fun before() {
        PowerMockito.mockStatic(LogUtil::class.java)
        AppDB.instance = mockk(relaxed = true)
        viewModel = spyk(GithubViewModel())
        view = mockk(relaxed = true)
        repo = mockk(relaxed = true)
        viewModel.view = view
        Whitebox.setInternalState(viewModel, "githubRepository", repo)
    }

    @Test
    fun `Validate if onNetworkError is triggered`() {
        // Arrange
        val error = "error"

        // Action
        viewModel.onRepositoryError(error)

        // Assert
        verify {
            view.onNetworkError(error)
        }
    }

    @Test
    fun `Validate if newRequest is triggered`() {
        // Arrange
        val login = "andrey2ag"

        // Action
        viewModel.newRequest(login)

        // Assert
        verify {
            repo.newRequest(login)
        }
    }

    @Test
    fun `Validate if LoadMore is triggered`() {

        // Action
        viewModel.loadMoreUsers()

        // Assert
        verify {
            repo.loadMoreUsers()
        }
    }
}