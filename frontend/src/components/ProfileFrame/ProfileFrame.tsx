import * as S from './ProfileFrame.style';

import Crown from '@_common/assets/crown.svg?url';
import DefaultProfile from '@_common/assets/default_profile.svg?url';
import { ImgHTMLAttributes, useState } from 'react';
import { Role } from '@_types/index';
import { useTheme } from '@emotion/react';

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
  const theme = useTheme();
  const [isLoaded, setIsLoaded] = useState(false);
  const handleError = (
    event: React.SyntheticEvent<HTMLImageElement, Event>,
  ) => {
    if (onError) {
      onError(event);
    }
    event.currentTarget.src = DefaultProfile;
  };

  return (
    <div css={S.profileBox()}>
      {role === 'MOIMER' ? <img src={Crown} css={S.profileCrown(width)} /> : ''}
      <div css={S.profileFrame({ width, height, borderWidth, theme })}>
        {!isLoaded && (
          <img src={DefaultProfile} css={S.profileImage()} alt="Placeholder" />
        )}
        <img
          css={S.profileImage({ isLoaded })}
          src={src || DefaultProfile}
          onLoad={() => setIsLoaded(true)}
          {...args}
          onError={handleError}
        />
      </div>
    </div>
  );
}
