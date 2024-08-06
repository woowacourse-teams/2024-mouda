export let pleasesData = [
  {
    pleaseId: 1,
    title: 'msw',
    description: '가나다라 마바사 아자차카타파하',
    isInterested: false,
    interestCount: 3,
  },
  {
    pleaseId: 2,
    title: 'msw',
    description: '가나다라 마바사 아자차카타파하',
    isInterested: false,
    interestCount: 3,
  },
  {
    pleaseId: 3,
    title: 'msw',
    description: '가나다라 마바사 아자차카타파하',
    isInterested: false,
    interestCount: 3,
  },
];

export function updatePlease(pleaseId: number, interesting: boolean) {
  pleasesData = pleasesData.map((please) =>
    please.pleaseId === pleaseId
      ? {
          ...please,
          isInterested: interesting,
          interestCount: interesting
            ? please.interestCount + 1
            : please.interestCount - 1,
        }
      : please,
  );
}
