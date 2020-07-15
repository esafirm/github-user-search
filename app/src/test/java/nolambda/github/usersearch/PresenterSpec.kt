package nolambda.github.usersearch

import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.specs.AbstractStringSpec
import io.kotlintest.specs.StringSpec
import io.mockk.clearAllMocks
import nolambda.github.usersearch.mvi.PresenterConfig

open class PresenterSpec(body: AbstractStringSpec.() -> Unit) : StringSpec({
    PresenterConfig.setTestMode()
    body.invoke(this)
}) {
    override fun afterTest(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }
}