import mill._

import mill.scalalib._
import mill.scalalib.publish._
import $file.plugins
import com.github.lolgab.mill.mima._

trait Common extends JavaModule with PublishModule {
  def publishVersion = "0.0.1"
  def pomSettings =
    PomSettings("", organization = "org", "", Seq(), VersionControl(), Seq())
}
object prev extends Common
object curr extends Common with Mima {
  override def mimaPreviousArtifacts = T(Agg(ivy"org:prev:0.0.1"))
  override def mimaCheckDirection = CheckDirection.Backward
}

def prepare() = T.command {
  prev.publishLocal(sys.props("ivy.home") + "/local")()
}

def verify() = T.command {
  curr.mimaReportBinaryIssues()()
  ()
}
