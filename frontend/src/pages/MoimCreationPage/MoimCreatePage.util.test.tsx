import {
  validateTitle,
  validateDate,
  validateTime,
  validatePlace,
  validateMaxPeople,
  validateAuthorNickname,
} from './MoimCreatePage.util';

describe('Validation Tests', () => {
  describe('validateTitle', () => {
    it('title의 문자열 길이가 1이상이거나 20이하일 경우 true를 반환한다.', () => {
      expect(validateTitle('a')).toBe(true);
      expect(validateTitle('a'.repeat(20))).toBe(true);
    });

    it('title의 문자열 길이가 1미만이거나 20초과일 경우 false를 반환한다.', () => {
      expect(validateTitle('')).toBe(false);
      expect(validateTitle('a'.repeat(21))).toBe(false);
    });
  });

  describe('validateDate', () => {
    beforeAll(() => {
      jest.useFakeTimers();
      jest.setSystemTime(new Date('2024-07-23T00:00:00Z'));
    });

    afterAll(() => {
      jest.useRealTimers();
    });

    it('2024년 7월 23일에 7월 23일로 설정하묜 true를 반환한다.', () => {
      const today = new Date().toISOString().split('T')[0];
      expect(validateDate(today)).toBe(true);
    });

    it('신청 시간보다 이전이거나 허용되지 않는 양식을 경우, false를 반환한다.', () => {
      expect(validateDate('2020-02-30')).toBe(false);
      expect(validateDate('2020-13-01')).toBe(false);
      expect(validateDate('invalid-date')).toBe(false);
      expect(validateDate('2023-07-22')).toBe(false);
    });
  });

  describe('validateTime', () => {
    it('00:00 형식이어야 true를 반환한다.', () => {
      expect(validateTime('12:34')).toBe(true);
      expect(validateTime('00:00')).toBe(true);
      expect(validateTime('23:59')).toBe(true);
    });

    it('00:00중 허용되지 않는 시간 양식을 경우, false를 반환한다.', () => {
      expect(validateTime('24:00')).toBe(false);
      expect(validateTime('12:60')).toBe(false);
      expect(validateTime('invalid-time')).toBe(false);
    });
  });

  describe('validatePlace', () => {
    it('place 문자열 길이가 1 이상이거나 100이하일 경우, true를 반환한다.', () => {
      expect(validatePlace('a')).toBe(true); // minimum length
      expect(validatePlace('a'.repeat(100))).toBe(true); // maximum length
    });

    it('place 문자열 길이가 1 미만이거나 101이상일 경우, false를 반환한다.', () => {
      expect(validatePlace('')).toBe(false); // less than minimum length
      expect(validatePlace('a'.repeat(101))).toBe(false); // more than maximum length
    });
  });

  describe('validateMaxPeople', () => {
    it('최대 인원수가 1이상이거나 99이하일경우 true를 반환한다.', () => {
      expect(validateMaxPeople(1)).toBe(true); // minimum
      expect(validateMaxPeople(99)).toBe(true); // maximum
    });

    it('최대 인원수가 0이하거나 100이상일 경우, false를 반환한다.', () => {
      expect(validateMaxPeople(0)).toBe(false); // less than minimum
      expect(validateMaxPeople(100)).toBe(false); // more than maximum
    });
  });

  describe('validateAuthorNickname', () => {
    it('닉네임 문자열 길이가 1이상이거나 10이하일 경우 true를 반환한다.', () => {
      expect(validateAuthorNickname('a')).toBe(true); // minimum length
      expect(validateAuthorNickname('a'.repeat(10))).toBe(true); // maximum length
    });

    it('닉네임 문자열 길이가 0이하이거나 11이상일 경우 false를 반환한다.', () => {
      expect(validateAuthorNickname('')).toBe(false); // less than minimum length
      expect(validateAuthorNickname('a'.repeat(11))).toBe(false); // more than maximum length
    });
  });
});
