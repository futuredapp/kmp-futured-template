import Foundation
import KMP

extension SkieSwiftFlow {
    // Helper to cast a Flow<Any> to Flow<WhatYouNeed>
    func cast<NewType>() -> SkieSwiftFlow<NewType> {
        // unsafeBitCast is safe here because SkieSwiftFlow is just a wrapper
        // around the same underlying Kotlin pointer, regardless of T.
        unsafeBitCast(self, to: SkieSwiftFlow<NewType>.self)
    }
}
