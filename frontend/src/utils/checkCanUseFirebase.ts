export default function checkCanUseFirebase() {
  if (location.hostname === 'localhost') return true;
  if (location.protocol !== 'https:') return false;
  if (navigator.userAgent.indexOf('KAKAO') >= 0) return false;
  return true;
}
