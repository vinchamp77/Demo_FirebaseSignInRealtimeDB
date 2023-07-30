package vtsen.hashnode.dev.signindemoapp.ui.data

data class UserData (
    val name:String?,
    val age: Int?,
    val favoriteFood:List<String>?
) {
    //Note: this is needed to read the data from the firebase database
    //firebase database throws this exception: UserData does not define a no-argument constructor
    constructor() : this(null, null, null)
}
