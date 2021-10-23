package com.example.listviewpractice

//profile은 이미지이다. 이미지는 보통 Int형의 resourse를 가지므로 Int형으로 선언.

//클래스 모델 객체를 선언
//만들고자 하는 listView에서 각 column에다가 뿌려주고자 하는 data의 틀을 잡는 것이다.
class User (val profile: Int, val name: String, val age: Int, val greet: String)