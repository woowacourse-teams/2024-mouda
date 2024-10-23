import * as S from './MoimSummary.style';

import { MoimInfo, MoimStatus } from '@_types/index';

import Tag from '@_pages/Moim/MoimDetailPage/components/Tag/Tag';
import { useTheme } from '@emotion/react';

interface MoimSummaryProps {
  moimInfo: Pick<MoimInfo, 'title' | 'status'>;
}

const getStatusKoKR = (status: MoimStatus) => {
  if (status === 'CANCELED') return '모임 취소';
  if (status === 'COMPLETED') return '모집 완료';
  if (status === 'MOIMING') return '모임 중';
  return '';
};

export default function MoimSummary(props: MoimSummaryProps) {
  const theme = useTheme();
  const {
    moimInfo: { title, status },
  } = props;

  return (
    <div css={S.containerStyle}>
      <div css={S.titleBox()}>
        <h1 css={S.title({ theme })} aria-label={'모임 제목' + title}>
          {title}
        </h1>
        <Tag
          status={status}
          aria-label={'모임 상태 ' + getStatusKoKR(status)}
        ></Tag>
      </div>
    </div>
  );
}
