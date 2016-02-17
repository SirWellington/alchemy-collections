Alchemy Collections
==============================================

[<img src="https://raw.githubusercontent.com/SirWellington/alchemy/develop/Graphics/Logo/Alchemy-Logo-v7-name.png" width="200">](https://github.com/SirWellington/alchemy)

## "Collect Yo'Self"

[![Build Status](https://travis-ci.org/SirWellington/alchemy-collections.svg)](https://travis-ci.org/SirWellington/alchemy-collections)


# Purpose
Part of the [Alchemy Collection](https://github.com/SirWellington/alchemy),
Alchemy Collections contains Algorithms and Operations you would intuitively
expect to perform on a java `Collection`.

Because odds are, if you want to do it, someone else does too,


# Download

To use, simply add the following maven dependency.

## Release

```xml
<dependency>
	<groupId>tech.sirwellington.alchemy</groupId>
	<artifactId>alchemy-collections</artifactId>
	<version>1.0</version>
</dependency>
```

## Snapshot

>First add the Snapshot Repository
```xml
<repository>
	<id>ossrh</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```

```xml
<dependency>
	<groupId>tech.sirwellington.alchemy</groupId>
	<artifactId>alchemy-collections</artifactId>
	<version>1.1-SNAPSHOT</version>
</dependency>
```

# API

## Lists
`sir.wellington.alchemy.collections.lists.Lists`

## Create
```java
List<User> users = Lists.create();
```

```java
List<User> defensiveCopy = Lists.immutableCopyOf(users);
```

## Combine
```java
List<People> clique = Lists.combine(family, coworkers, enemies, neighbors);
```

### Any String
```java
List<String> strings = ...;
String anyString = Lists.oneOf(strings);
```


## Maps
`sir.wellington.alchemy.collections.maps.Maps`

## Create
```java
Map<Integer, String> map = Maps.create();

Map<Integer, String> defensiveCopy = Maps.immutableCopyOf(map);
```

## Merge
```java
Map<String, Object> first = ...;
Map<String, Object> second = ...;
Map<String, Object> merged = Maps.merge(first, second);
```

## Sets
`sir.wellington.alchemy.collections.sets.Sets`


# [Javadocs](http://www.javadoc.io/doc/tech.sirwellington.alchemy/alchemy-collections/)

# Requirements

+ Java 8
+ Maven

# Building
This project builds with maven. Just run a `mvn clean install` to compile and install to your local maven repository


# Release Notes

## 1.0
Initial Public Release.


# License

This Software is licensed under the Apache 2.0 License

http://www.apache.org/licenses/LICENSE-2.0
