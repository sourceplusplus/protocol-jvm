package spp.protocol.instrument

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
enum class DurationStep(val pattern: String) {
    DAY("yyyy-MM-dd"),
    HOUR("yyyy-MM-dd HH"),
    MINUTE("yyyy-MM-dd HHmm"),
    SECOND("yyyy-MM-dd HHmmss")
}
