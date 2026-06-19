plugins {
    id("java")
}

dependencies {
    implementation(project(":core"))
    implementation("org.apache.poi:poi:5.4.1")
    implementation("org.apache.poi:poi-ooxml:5.4.1")
}