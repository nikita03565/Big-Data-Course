package list

import java.util.concurrent.atomic.AtomicMarkableReference

class Node<T>(value: T, next: Node<T>?) {
    var next: AtomicMarkableReference<Node<T>> = AtomicMarkableReference(next, false)
    var value: T = value

}

class LockFreeList<T> {
    var head: AtomicMarkableReference<Node<T?>>

    init {
        val headNode = Node<T?>(null, null)
        head = AtomicMarkableReference(headNode, false)
    }

    fun addFirst(value: T) {
        addAfter(head.reference.value, value)
    }

    fun addAfter(after: T?, value: T): Boolean {
        var sucessful = false
        while (!sucessful) {
            var found = false
            var node = head.reference
            while (node != null && !isRemoved(node)) {
                if (isEqual(node.value, after) && !node.next.isMarked) {
                    found = true
                    val nextNode = node.next.reference
                    val newNode = Node(value, nextNode)
                    sucessful = node.next.compareAndSet(nextNode, newNode, false, false)
                    break
                }
                node = node.next.reference
            }
            if (!found) {
                return false
            }
        }
        return true
    }

    fun remove(value: T): Boolean {
        var sucessful = false
        while (!sucessful) {
            var found = false
            var node = head.reference
            var nextNode = node.next.reference
            while (nextNode != null) {
                if (!isRemoved(nextNode) && isEqual(nextNode.value, value)) {
                    found = true
                    logicallyRemove(nextNode)
                    sucessful = physicallyRemove(node, nextNode)
                    break
                }
                node = nextNode
                nextNode = nextNode.next.reference
            }
            if (!found) {
                return false
            }
        }
        return true
    }

    fun logicallyRemove(node: Node<T?>) {
        while (!node.next.attemptMark(node.next.reference, true)) {
        }
    }

    fun physicallyRemove(leftNode: Node<T?>, node: Node<T?>): Boolean {
        var rightNode = node
        do {
            rightNode = rightNode.next.reference
        } while (rightNode != null && isRemoved(rightNode))
        return leftNode.next.compareAndSet(node, rightNode, false, false)
    }

    fun isRemoved(node: Node<T?>): Boolean {
        return node.next.isMarked
    }

    fun isEqual(arg0: T?, arg1: T?): Boolean {
        return if (arg0 == null) {
            arg0 === arg1
        } else {
            arg0 == arg1
        }
    }

    fun print() {
        println("List content:")
        var node = head.reference.next.reference
        while (node != null) {
            println(node.value)
            node = node.next.reference
        }
    }
}

fun main() {
    // simple test to make sure this works in 1 thread
    val list = LockFreeList<Int>()
    list.addFirst(0)
    for (i in 1..100) {
        list.addAfter(i - 1, i)
    }
    list.remove(10)
    list.print()
}