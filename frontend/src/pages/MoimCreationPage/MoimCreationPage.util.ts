// validation.js

import POLICIES from '@_constants/poclies';

export const validateTitle = (title: string) =>
  POLICIES.minimumTitleLength <= title.length &&
  title.length <= POLICIES.maximumTitleLength;

export const validateDate = (date: string) => {
  if (date === '') {
    return true;
  }
  const nowDate = new Date();
  const nowDateYyyymmdd = `${nowDate.getFullYear()}-${(nowDate.getMonth() + 1).toString().padStart(2, '00')}-${nowDate.getDate().toString().padStart(2, '00')}`;
  return date >= nowDateYyyymmdd && POLICIES.yyyymmddDashRegex.test(date);
};

export const validateTime = (time: string) => {
  if (time === '') {
    return true;
  }
  if (!POLICIES.hhmmRegex.test(time)) {
    return false;
  }

  const now = new Date();
  const [inputHour, inputMinute] = time.split(':').map(Number);
  const inputTime = new Date(
    now.getFullYear(),
    now.getMonth(),
    now.getDate(),
    inputHour,
    inputMinute,
  );

  if (inputTime < now) {
    return false;
  }
  return true;
};

export const validatePlace = (place: string) => {
  if (place === '') {
    return true;
  }
  return (
    POLICIES.minimumPlaceLength <= place.length &&
    place.length <= POLICIES.maximumPlaceLength
  );
};
export const validateMaxPeople = (maxPeople: number) =>
  POLICIES.minimumMaxPeople <= maxPeople &&
  maxPeople <= POLICIES.maximumMaxPeople;

export const validateAuthorNickname = (authorNickname: string) =>
  POLICIES.minimumAuthorNicknameLength <= authorNickname.length &&
  authorNickname.length <= POLICIES.maximumAuthorNicknameLength;

/**
 * @description 브라우저의 네비게이션 타입을 구분하는 함수.
 * 링크 클릭, URL 직접 입력으로 페이지에 들어온 경우를 구분하기 위해 사용.
 * react router로는 감지할 수 없어서 사용.
 * ref: https://developer.mozilla.org/en-US/docs/Web/API/PerformanceNavigationTiming/type
 */
const getBrowserNavigationType = ():
  | 'navigate'
  | 'reload'
  | 'back_forward'
  | 'prerender'
  | undefined => {
  const navEntries = performance.getEntriesByType('navigation');
  if (navEntries.length > 0) {
    const navEntry = navEntries[0] as PerformanceNavigationTiming;
    return navEntry.type;
  }
};

export const isApprochedByUrl = () => {
  return getBrowserNavigationType() === 'navigate';
};
