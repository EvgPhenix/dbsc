
object Main extends App {
  if (args.length == 2) ScriptDefinition.getScript(args(0), args(1))
  else if (args.length == 1) ScriptDefinition.getScript(args(0), "output.txt")
  else ScriptDefinition.getScript("partner.xml", "output.txt")
}
