/**
 * home.js
 * 
 * DOM (Document Object Model) Tree 객체화 - 문서를 처리하는 모델
 * 
 * jQuery(표현식)함수검색-객체검색 Selector 표현식 ==> DOM에서 객체(Element, Tag)를 찾는 표현식
 * 
 * 1. 태그이름 <button></button> ==> 표현식 => button
 * 2. class ==> .class name  <button class="btn"></button> ==> .btn
 * 3. id    ==> #SlideDown   <button id="SlideDown"></button> ==> #SlideDown
 * 
 * jQuery함수 : jQuery((표현식))
 * jQuery Wrapper 객체, jQuery 객체(DOM객체를 Wrapping-기능향상위해), jQuery 집합 객체: jQuery() 함수가 리턴하는 Type
 * 
 * jQuery() as $() (== $() )
 * 
 */

function xxx(event) { //Handler (event 객체 parameter로)
	//alert("button click...");
	console.log("button click... event = " + event);
	//$('img').slideToggle(1000); //1초
	$('img').fadeToggle(1000);
}

//$('button').click(handler) button객체를 찾아서 click event를 달도록 처리
$('span').click(xxx).draggable(); //jQuery의 특징-자기객체를 리턴, click(), draggable() Method chain
//$('button').click(xxx).draggable(); 
//$('button').draggable();
$('img').draggable();







