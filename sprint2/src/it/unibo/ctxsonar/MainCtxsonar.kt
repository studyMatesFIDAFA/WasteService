/* Generated by AN DISI Unibo */ 
package it.unibo.ctxsonar
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "10.5.5.5", this, "modellosprint2.pl", "sysRules.pl"
	)
}
