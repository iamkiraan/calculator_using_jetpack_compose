class Soilder {
    val name = "Kiran"
    val rank = "inspector"
    val isMissing = true
    var status = "$rank $name"
    fun getStatus() {
        if (isMissing) {

            status = "$status is ready"
        } else {
            status = "$status is not ready"
        }
    }
}
