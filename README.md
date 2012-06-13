find-duplicates
===============

Playing with various implementations of bloom filters ...

Cassandra's bloom filter implementation works best for my use case as it handles sets of larger cardinality (64 * 2**32-1) by using OpenBitSet which is also faster than BitSet.