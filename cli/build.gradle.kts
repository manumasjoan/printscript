plugins {
    id("printscript.java-application-conventions")
}

dependencies{
    implementation(project(":parser"))
    implementation(project(":lexer"))
    implementation(project(":interpreter"))

}