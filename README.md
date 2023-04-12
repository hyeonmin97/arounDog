# arounDog (Dog Walking Application)
arounDog은 around + dog의 합성어로 주변에 있는 반려동물의 정보를 확인할 수 있는 산책 어플리케이션입니다. 

밖에서 걷다보면 반려동물과 함께 즐거운 시간을 보내는 사람들을 자주 만날 수 있습니다. 

반려동물을 위해 피하고 싶은 견종이 있거나 본인이 기피하는 견종이 있으면 주변을 둘러보면서 해당 견종이 있는지 확인하는 방법밖에 없습니다. 

### arounDog은 사용자의 위치 정보를 바탕으로 기피하는 반려동물이 접근하면 사용자에게 알림으로써 산책시간을 온전하게 즐길 수 있게 해줍니다.
<br>

## 시연 영상
<div align="center">
    <table style="width:100%;text-align:center">
    <tr style="text-align:center">
        <td>
            <div align="center"> <h3>로그인</h3> </div>
        </td>
        <td>
            <div align="center"> <h3>회피종 선택</h3> </div>
        </td>
        <td>
            <div align="center"> <h3>추천 산책로 확인</h3> </div>
        </td>
    </tr>
    <tr>
        <td><img style="display: block;margin-left: auto;margin-right: auto;" src = "https://user-images.githubusercontent.com/58110946/231223193-999faa8d-f1c0-4d05-978f-63228046db1f.gif" alt="로그인"></td>
        <td><img style="display: block;margin-left: auto;margin-right: auto;" src = "https://user-images.githubusercontent.com/58110946/231234540-828d6474-ecd6-40ea-8cba-a80f60062e6d.gif" alt="회피종"></td>
        <td><img style="display: block;margin-left: auto;margin-right: auto;" src = "https://user-images.githubusercontent.com/58110946/231234342-9123d76e-7181-4ffe-b51b-d25a6109831b.gif" alt="추천 산책로">
        </td>
    </tr>
    </table>  
    <table>
    <tr>
        <td>
            <div align="center"> <h3>산책하기</h3> </div>
        </td>
        <td>
            <div align="center"> <h3>이전 산책 조회</h3> </div>
        </td>
    </tr>
    <tr>
        <td><img style="display: block;margin-left: auto;margin-right: auto;" src = "https://user-images.githubusercontent.com/58110946/231233714-ec5ec6ac-fab3-459e-966e-466cd7b525c7.gif" alt="산책"></td>
        <td><img style="display: block;margin-left: auto;margin-right: auto;" src = "https://user-images.githubusercontent.com/58110946/231234166-32dcb645-6253-46cf-82e7-1a6a2c7c2974.gif" alt="산책 조회"></td>
    </tr>  
    </table>
 </div>
<br>

## 주요 기능
- 로그인 / 회원가입
- 아이디 / 비밀번호 찾기
- 산책 경로 트래킹
- 선호하지 않는 강아지 설정 / 알림
- 산책 정보 확인
- 추천 산책경로 확인
<br>

## 기술 스택
- Android(Kotlin) : arounDog 어플리케이션
- Java(Spring API Server) : arounDog 서버
- Python : 산책경로 중복 제거 스크립트
<br>

## 시스템 구성도
![image](https://user-images.githubusercontent.com/54983139/208286560-e2f4b60e-021c-43dc-b333-efd07665f3b8.png)

<br>

## 서버
<div align="center">
<img src = "https://user-images.githubusercontent.com/54983139/193743481-d3762456-f786-4eb8-908a-70202afb49cd.png">

<br>

자세한 내용은 [여기](https://github.com/hyeonmin97/aroundog_server)에서 확인하실 수 있습니다.
</div>
<br>

## 데이터베이스
![aroundog](https://user-images.githubusercontent.com/58110946/204068160-ab257cfc-eb57-4d8e-a67c-941de43777a9.png)
<div align="center">

|테이블명|설명|
| :--- | :--- |
| walk | 산책 후 저장되는 산책에 대한 정보 |
| walk_deduplication | 중복이 제거된 산책 정보 |
| user | 사용자 정보 |
| coordinate | 사용자의 마지막 산책 위치, 현재 산책 여부 |
| user_dog | 반려견 정보 |
| dog_img | 강아지 사진, 사진이 저장된 경로 |
| dog | 강아지 종|
| good | 유저가 좋아요를 누른 산책경로 |
| bad | 유저가 싫어요를 누른 산책경로 |

</div>
<br>

## 팀원

<div align="center">

|<img src="https://user-images.githubusercontent.com/58110946/231477577-c007f727-6133-450d-9168-190a810835c0.png" width="100px">|<img src="https://user-images.githubusercontent.com/58110946/231477714-9f0a23dd-534e-4dff-a145-38148d3ce6dd.png" width="100px">|<img src="https://user-images.githubusercontent.com/58110946/231477233-b588da79-195e-4d9f-be35-b5e3d8c9a6a4.png" width="100px">|
|:--:|:--:|:--:|
|[박현민](https://github.com/hyeonmin97)|[이강석](https://github.com/Leeks1997)|[이도원](https://github.com/qqqqlss)|
|스프링 서버<br>안드로이드<br>파이썬|안드로이드|안드로이드|

</div>
