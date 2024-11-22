import POLICIES from '@_constants/poclies';

export const validateTitle = (title: string) =>
  POLICIES.minimumTitleLength <= title.length &&
  title.length <= POLICIES.maximumTitleLength;

export const validateDescription = (description: string) =>
  POLICIES.minPleaseDescription <= description.length &&
  description.length <= POLICIES.maxPleaseDescription;
