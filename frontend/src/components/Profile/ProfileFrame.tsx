import { ImgHTMLAttributes } from 'react';
import * as S from './ProfileFrame.style';
import EmptyProfile from '@_common/assets/empty_profile.svg?url';
import Crown from '@_common/assets/crown.svg?url';
import { Role } from '@_types/index';

interface ProfileFrameProps extends ImgHTMLAttributes<HTMLImageElement> {
  role?: Role;
  width: number;
  height: number;
  borderWidth?: number;
}

export default function ProfileFrame(props: ProfileFrameProps) {
  const {
    width,
    height,
    borderWidth = 0.5,
    onError,
    role,
    src,
    ...args
  } = props;

  const handleError = (
    event: React.SyntheticEvent<HTMLImageElement, Event>,
  ) => {
    if (onError) {
      onError(event);
    }
    event.currentTarget.src = EmptyProfile;
  };

  return (
    <div css={S.profileBox()}>
      {role === 'moimer' ? <img src={Crown} css={S.profileCrown(width)} /> : ''}
      <div css={S.profileFrame(width, height, borderWidth)}>
        <img
          css={S.profileImage}
          src={src || EmptyProfile}
          {...args}
          onError={handleError}
        />
      </div>
    </div>
  );
}
