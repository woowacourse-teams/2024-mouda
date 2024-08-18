import { getMessaging, onMessage } from 'firebase/messaging';
import { app } from './initFirebase';

// Firebase Messaging 인스턴스 가져오기
const messaging = getMessaging(app);

onMessage(messaging, (payload) => {
  console.log('포그라운드 알림 도착: ', payload);

  const notificationTitle = payload.notification?.title;
  const notificationOptions = {
    body: payload.notification?.body,
    icon: payload.notification?.icon, // 알림 아이콘 설정
    data: { link: payload.fcmOptions?.link || '/' }, // 알림 클릭 시 이동할 링크 설정
  };

  if (Notification.permission === 'granted') {
    const notification = new Notification(
      notificationTitle!,
      notificationOptions,
    );

    notification.onclick = function (event) {
      event.preventDefault(); // 기본 동작 방지 (필요한 경우)

      // 알림 클릭 시 페이지 이동
      window.open(notificationOptions.data.link, '_blank');
    };
  } else {
    console.warn('알림 권한이 허용되지 않았습니다.');
  }
});
