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

export const isApproachedByUrl = () => {
  return getBrowserNavigationType() === 'navigate';
};
