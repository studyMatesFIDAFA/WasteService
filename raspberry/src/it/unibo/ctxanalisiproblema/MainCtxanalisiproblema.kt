/* Generated by AN DISI Unibo */ 
package it.unibo.ctxanalisiproblema
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "169.254.239.199", this, "raspberry.pl", "sysRules.pl"
	)
}

