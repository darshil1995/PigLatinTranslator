package com.piglatin.translator

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    PigTranslatorTest::class
)
class TestSuite