export const yyyymmddToKorean = (yyyymmdd: string, seperator: string = '-') => {
  const yyyymmddArray = yyyymmdd.split(seperator).map(Number);
  if (yyyymmddArray.length !== 3) {
    throw new Error('올바른 날짜 형식이 아닙니다');
  }
  if (yyyymmddArray.some((el) => Number.isNaN(el))) {
    throw new Error('올바른 날짜 형식이 아닙니다');
  }

  const [year, month, date] = yyyymmddArray;
  const nowYear = new Date().getFullYear();
  const result = `${month}월 ${date}일`;
  if (year !== nowYear) {
    return `${year}년 ${result}`;
  }
  return result;
};

export const hhmmToKorean = (hhmm: string, seperator: string = ':') => {
  const hhmmArray = hhmm.split(seperator).map(Number);
  if (hhmmArray.length < 2) {
    throw new Error('올바른 시간 형식이 아닙니다');
  }
  if (hhmmArray.some((el) => Number.isNaN(el))) {
    throw new Error('올바른 시간 형식이 아닙니다');
  }

  const [hour, minute] = hhmmArray;

  if (minute === 0) {
    return `${hour}시`;
  }

  return `${hour}시 ${minute}분`;
};
