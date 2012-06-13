find-duplicates
===============

Playing with various implementations of bloom filters ...

Cassandra's bloom filter implementation works best for my use case as it handles sets of larger cardinality (64 * 2**32-1) by using OpenBitSet which is also faster than BitSet.

### Quick start
1. Build with maven

    mvn compile assembly:single

2. Define bloom filter size and false positive probability properties

    bloom.elements=2000000000
    bloom.probability=0.0001

3. Need jamm.jar if you want to see memory size (https://github.com/jbellis/jamm)

4. Run it

    java -javaagent:./jamm-0.2.6-SNAPSHOT.jar -jar find-duplicates-jar-with-dependencies.jar -c <PROPERTIES FILE> -d <BASE DATA DIR> -r <FILE REGEX>