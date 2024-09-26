import GET_ROUTES from '@_common/getRoutes';
import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import { PropsWithChildren } from 'react';
import { useNavigate } from 'react-router-dom';

import { useTheme } from '@emotion/react';

function InformationItem(props: PropsWithChildren) {
  const { children } = props;

  return <div>{children}</div>;
}
function Title(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <h3 css={theme.typography.b2}>{children}</h3>;
}
function Content(props: PropsWithChildren) {
  const { children } = props;

  return children;
}
InformationItem.Title = Title;
InformationItem.Content = Content;

export default function BetDetailPage() {
  const navigate = useNavigate();

  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <div onClick={() => navigate(GET_ROUTES.nowDarakbang.bet())}>
            <BackArrowButton />
          </div>
        </InformationLayout.Header.Left>
      </InformationLayout.Header>

      <InformationLayout.ContentContainer>
        <InformationItem>
          <InformationItem.Title>제목</InformationItem.Title>
          <InformationItem.Content>
            <div>커피빵 ㄱㄱ</div>
          </InformationItem.Content>
        </InformationItem>

        <InformationItem>
          <InformationItem.Title>참가인원</InformationItem.Title>
          <InformationItem.Content>
            <div>3/5</div>
          </InformationItem.Content>
        </InformationItem>

        <InformationItem>
          <InformationItem.Title>결과 확인 시간</InformationItem.Title>
          <InformationItem.Content>
            <div>5분 후</div>
          </InformationItem.Content>
        </InformationItem>
      </InformationLayout.ContentContainer>

      <InformationLayout.BottomButtonWrapper>
        {/* <Button
          
        >나는 불나방! ㄱㄱ</Button> */}
      </InformationLayout.BottomButtonWrapper>
    </InformationLayout>
  );
}
