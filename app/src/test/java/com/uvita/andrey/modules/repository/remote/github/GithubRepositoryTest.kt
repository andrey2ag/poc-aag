package com.uvita.andrey.modules.repository.remote.github

import androidx.lifecycle.LiveData
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.uvita.andrey.general.LogUtil
import com.uvita.andrey.models.entities.GitHubUser
import com.uvita.andrey.modules.githubUsers.GithubView
import com.uvita.andrey.modules.githubUsers.GithubViewModel
import com.uvita.andrey.modules.repository.local.AppDB
import com.uvita.andrey.modules.repository.local.dao.GitHubDao
import io.mockk.every
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
import retrofit2.Retrofit

@PowerMockIgnore("kotlin.*", "javax.net.ssl.*")
@RunWith(PowerMockRunner::class)
@PrepareForTest(Retrofit::class, LogUtil::class)
class GithubRepositoryTest {
    private lateinit var viewModel: GithubViewModel
    private lateinit var restInterface: GithubAPI
    private lateinit var repo: GithubRepository
    private lateinit var githubDao: GitHubDao

    @Before
    fun before() {
        PowerMockito.mockStatic(LogUtil::class.java)
        PowerMockito.mockStatic(Retrofit::class.java)

        viewModel = mockk(relaxed = true)
        restInterface = mockk(relaxed = true)
        githubDao = mockk()
        val testDb: AppDB = mockk()
        every { githubDao.getUsers() } returns mockk()
        every { testDb.githubDao() } returns githubDao
        repo = spyk(GithubRepository(viewModel, testDb))

    }

    @Test
    fun `Validate if new page is requested`() {
        // Arrange
        val currentLogin = "andrey"
        Whitebox.setInternalState(repo, "currentLogin", currentLogin)
        Whitebox.setInternalState(repo, "loadedEntries", 1)
        Whitebox.setInternalState(repo, "totalEntries", 5)

        // Action
        repo.loadMoreUsers()

        // Assert
        verify {
            repo.getGitHubUsers(currentLogin, 2)
        }
    }

    @Test
    fun `Validate do not request if no more entries available`() {
        // Arrange
        val currentLogin = "andrey"
        Whitebox.setInternalState(repo, "currentLogin", currentLogin)
        Whitebox.setInternalState(repo, "loadedEntries", 5)
        Whitebox.setInternalState(repo, "totalEntries", 5)

        // Action
        repo.loadMoreUsers()

        // Assert
        verify {
            viewModel.onRepositoryError("End of results")
        }
    }

    @Test
    fun `New request is set with default values`() {
        // Arrange
        val currentLogin = "andrey"
        Whitebox.setInternalState(repo, "currentLogin", "uvita")
        Whitebox.setInternalState(repo, "currentPage", 3)

        every { githubDao.removeAllEntries() } returns mockk()
        // Action
        repo.newRequest(currentLogin)

        // Assert
        verify {
            repo.cleanAppDB()
            repo.getGitHubUsers(currentLogin, FIRST_PAGE)
        }
    }

    @Test
    fun `Verify DB provides livedata`() {
        // Arrange
        val currentLogin = "andrey"
        Whitebox.setInternalState(repo, "currentLogin", "uvita")
        Whitebox.setInternalState(repo, "currentPage", 3)

        every { githubDao.removeAllEntries() } returns mockk()
        // Action
        repo.getGitHubUsers(currentLogin, 1)

        // Assert
        verify {
            githubDao.getUsers()
        }
    }

}