import ProfileFrame from './ProfileFrame';
import * as S from './ProfileCard.style';
import { useTheme } from '@emotion/react';

interface ProfileCardProps {
  name: string;
  src: string;
}
export default function ProfileBox(props: ProfileCardProps) {
  const { name, src } = props;
  const theme = useTheme();
  return (
    <div css={S.ProfileCard}>
      <ProfileFrame width={7} height={7} src={src} />
      <div css={S.ProfileName({ theme })}>{name}</div>
    </div>
  );
}
