import { MoimInfo } from '@_types/index';
import * as S from './MoimSummary.style';

interface MoimSummaryProps {
  moimInfo: Pick<MoimInfo, 'title' | 'authorNickname'>;
}

export default function MoimSummary(props: MoimSummaryProps) {
  const {
    moimInfo: { title, authorNickname },
  } = props;

  return (
    <div css={S.containerStyle}>
      <h1 css={S.titleStyle}>{title}</h1>
      <div css={S.contentStyle}>
        <span css={S.contentKeyStyle}>작성자</span>
        <span css={S.contentValueStyle}>{authorNickname}</span>
      </div>
    </div>
  );
}
