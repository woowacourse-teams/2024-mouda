import { ImgHTMLAttributes } from 'react';
import * as S from './ProfileFrame.style';
import EmptyProfile from '@_common/assets/empty_profile.svg?url';

interface ProfileFrameProps extends ImgHTMLAttributes<HTMLImageElement> {
  width: string | number | undefined;
  height: string | number | undefined;
}
export default function ProfileFrame(props: ProfileFrameProps) {
  const { width, height, onError, ...args } = props;

  const handleError = (
    event: React.SyntheticEvent<HTMLImageElement, Event>,
  ) => {
    if (onError) {
      onError(event);
    }
    event.currentTarget.src = EmptyProfile;
  };

  return (
    <div css={S.profileFrame(width, height)}>
      <img css={S.profileImage} {...args} onError={handleError} />
    </div>
  );
}
