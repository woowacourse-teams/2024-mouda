import { BetDetail } from '@_types/index';

const calculateMinutesUntilDeadline = (
  deadline: BetDetail['deadline'],
): number => {
  const deadlineDate = new Date(deadline);

  const now = new Date();

  const differenceInMilliseconds = deadlineDate.getTime() - now.getTime();

  const differenceInMinutes = Math.floor(
    differenceInMilliseconds / (1000 * 60),
  );

  return differenceInMinutes;
};

export default calculateMinutesUntilDeadline;
