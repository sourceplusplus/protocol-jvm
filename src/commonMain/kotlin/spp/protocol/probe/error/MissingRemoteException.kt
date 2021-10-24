package spp.protocol.probe.error

class MissingRemoteException(remote: String) : RuntimeException(remote) {
//    @Synchronized
//    override fun fillInStackTrace(): Throwable {
//        return this
//    }
//
//    override fun toString(): String {
//        return localizedMessage
//    }
}
