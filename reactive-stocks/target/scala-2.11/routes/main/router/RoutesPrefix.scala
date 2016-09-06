
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/brycerich/Desktop/Fall2016/ECE4012/SeniorDesign/RamblinTek/reactive-stocks/conf/routes
// @DATE:Tue Sep 06 17:47:14 EDT 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
