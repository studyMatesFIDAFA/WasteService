/* Generated by AN DISI Unibo */ 
package it.unibo.ctxWasteServiceTest
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "sprint1test.pl", "sysRules.pl"
	)
}
