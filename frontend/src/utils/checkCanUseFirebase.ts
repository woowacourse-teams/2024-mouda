import { isSupported } from 'firebase/messaging';
export default async function checkCanUseFirebase() {
  if (location.hostname === 'localhost') return true;
  if (location.protocol !== 'https:') return false;
  if (navigator.userAgent.indexOf('KAKAO') >= 0) return false;
  const messagingSupported = await isSupported();
  if (!messagingSupported) {
    console.error("This browser doesn't support Firebase Messaging.");
    return false;
  }
  return true;
}
