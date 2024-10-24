import * as S from './Dimmer.style';

export default function Dimmer({ onClick }: { onClick: () => void }) {
  return <div onClick={onClick} css={S.dimmer} />;
}
