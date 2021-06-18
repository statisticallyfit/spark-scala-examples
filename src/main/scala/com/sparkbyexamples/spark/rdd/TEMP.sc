import scala.reflect.macros.Context
import scala.language.experimental.macros

object Macros {
	def sourceFile: String = macro sourceFileImpl
	def sourceFileImpl(c: Context) = {
		import c.universe._
		c.Expr[String](Literal(Constant(c.enclosingUnit.source.path.toString)))
	}
}

Macros.sourceFile