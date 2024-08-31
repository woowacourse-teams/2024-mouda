import BackLogo from '@_common/assets/back.svg';
import GET_ROUTES from '@_common/getRoutes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import NotificationList from '@_components/NotificationList/NotificationList';
import { useNavigate } from 'react-router-dom';
import { Fragment, useEffect, useState } from 'react';
import Modal from '@_components/Modal/Modal';
import Button from '@_components/Button/Button';
import { useTheme } from '@emotion/react';
import * as S from './NotificationPage.style';
import RefreshButton from '@_components/RefreshButton/RefreshButton';

export default function NotificationPage() {
  const navigate = useNavigate();
  const theme = useTheme();
  const [isModalOpen, setIsModalOpen] = useState(false);
  useEffect(() => {
    if (window.Notification && window.Notification.permission === 'denied') {
      setIsModalOpen(true);
    }
  }, []);

  const handleModalClose = () => {
    setIsModalOpen(false);
  };

  return (
    <Fragment>
      <InformationLayout>
        <InformationLayout.Header>
          <InformationLayout.Header.Left>
            <div onClick={() => navigate(GET_ROUTES.nowDarakbang.main())}>
              <BackLogo />
            </div>
          </InformationLayout.Header.Left>
          <InformationLayout.Header.Right>
            <RefreshButton />
          </InformationLayout.Header.Right>
          <InformationLayout.Header.Center>
            알림
          </InformationLayout.Header.Center>
        </InformationLayout.Header>
        <InformationLayout.ContentContainer>
          <NotificationList />
        </InformationLayout.ContentContainer>
      </InformationLayout>
      {isModalOpen && (
        <Modal onClose={handleModalClose}>
          <div css={S.ModalContent({ theme })}>
            알림을 허용해야 모임에 대한 알림을 받을 수 있습니다.
            <br /> 브라우저 환경에서 알림을 허용해주세요!
          </div>
          <Button shape="bar" onClick={handleModalClose}>
            닫기
          </Button>
        </Modal>
      )}
    </Fragment>
  );
}
