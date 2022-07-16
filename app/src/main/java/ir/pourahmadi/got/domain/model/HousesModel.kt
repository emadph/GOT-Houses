package ir.pourahmadi.got.domain.model

data class HousesModel(
    var url: String? = null,
    var name: String? = null,
    var region: String? = null,
    var flagDesc: String? = null,
    var words: String? = null,
    var founded: String? = null,
    var titles: List<String>? = null
)


