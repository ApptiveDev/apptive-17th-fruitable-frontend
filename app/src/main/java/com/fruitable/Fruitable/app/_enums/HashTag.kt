package com.fruitable.Fruitable.app._enums

sealed class HashTag(val tag: String, val name: String){
    object all: HashTag("all", "전체")
    object pineapple: HashTag("pineapple", "# 파인애플")
    object farm: HashTag("farm", "# 농장")
    object apple: HashTag("apple", "# 사과")
    object banana: HashTag("banana", "# 바나나")
    object strawberry: HashTag("strawberry", "# 딸기")
    object cucumber: HashTag("cucumber", "# 오이")
    object eggplant: HashTag("eggplant", "# 가지")
}
