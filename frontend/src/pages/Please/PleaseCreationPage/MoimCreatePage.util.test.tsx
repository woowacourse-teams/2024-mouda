import { validateTitle, validateDescription } from './PleaseCreationPage.util';

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

  describe('validateDescription', () => {
    it('title의 문자열 길이가 5이상이거나 10000이하일 경우 true를 반환한다.', () => {
      expect(validateDescription('aaaaaa')).toBe(true);
      expect(validateDescription('a'.repeat(20))).toBe(true);
    });

    it('title의 문자열 길이가 5미만이거나 20초과일 경우 false를 반환한다.', () => {
      expect(validateDescription('aaaa')).toBe(false);
      expect(validateDescription('a'.repeat(100000))).toBe(false);
    });
  });
});
