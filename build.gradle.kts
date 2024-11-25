import java.util.*

plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.boot:spring-boot-devtools")

//	implementation("org.springframework.boot:spring-boot-starter-jdbc")//순수 jdbc 강의 삽입
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")//얘가 jdbc 관련 라이브러리 포함함
	runtimeOnly("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}



//tasks.withType<Test> {
//	useJUnitPlatform()
//}
////빌드에서 테스트를 해버림

val frontendDir = "$projectDir/src/main/reactfront"

sourceSets {
	main {
		resources {
			srcDirs("$projectDir/src/main/resources")
		}
	}
}

tasks.named("processResources") {
	dependsOn("copyReactBuildFiles")
}

tasks.register<Exec>("installReact") {
	workingDir = file(frontendDir)
	inputs.dir(frontendDir)
	group = BasePlugin.BUILD_GROUP
	if (System.getProperty("os.name").lowercase(Locale.ROOT).contains("windows")) {
		commandLine("npm.cmd", "audit", "fix")
		commandLine("npm.cmd", "install")
	} else {
		commandLine("npm", "audit", "fix")
		commandLine("npm", "install")
	}
}

tasks.register<Exec>("buildReact") {
	dependsOn("installReact")
	workingDir = file(frontendDir)
	inputs.dir(frontendDir)
	group = BasePlugin.BUILD_GROUP
	if (System.getProperty("os.name").lowercase(Locale.ROOT).contains("windows")) {
		commandLine("npm.cmd", "run-script", "build")
	} else {
		commandLine("npm", "run-script", "build")
	}
}

tasks.register<Copy>("copyReactBuildFiles") {
	dependsOn("buildReact")
	from("$frontendDir/build")
	into("$projectDir/src/main/resources/static")
}

tasks {

	processResources {
		duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.INCLUDE
	}
}