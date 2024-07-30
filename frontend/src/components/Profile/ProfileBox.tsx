import ProfileFrame from './ProfileFrame';
import * as S from './ProfileBox.style';
import { useTheme } from '@emotion/react';

interface ProfileBoxProps {
  name: string;
  src: string;
}
export default function ProfileBox(props: ProfileBoxProps) {
  const { name, src } = props;
  const theme = useTheme();
  return (
    <div css={S.ProfileBox}>
      <ProfileFrame width={'7rem'} height={'7rem'} src={src} />
      <div css={S.ProfileName({ theme })}>{name}</div>
    </div>
  );
}
